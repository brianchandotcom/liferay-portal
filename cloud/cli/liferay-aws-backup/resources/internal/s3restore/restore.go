package s3restore

import (
	"context"
	"fmt"
	"log"
	"time"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/backup"

	"github.com/liferay/liferay-portal/cloud/cli/liferay-aws-backup/internal/restorejob"
)

type Params struct {
	BackupVaultName  string
	IamRoleARN       string
	RecoveryPointARN string
	TargetBucketName string
	Wait             bool
	WaitTimeout      time.Duration
}

func Run(
	ctx context.Context,
	backupClient *backup.Client,
	params Params,
) error {
	log.Printf(
		"Starting S3 restore job for target bucket %q\n",
		params.TargetBucketName)

	restoreOutput, err := backupClient.StartRestoreJob(
		ctx,
		&backup.StartRestoreJobInput{
			RecoveryPointArn: aws.String(params.RecoveryPointARN),
			IamRoleArn:       aws.String(params.IamRoleARN),
			ResourceType:     aws.String("S3"),
			Metadata: map[string]string{
				"DestinationBucketName": params.TargetBucketName,
				"NewBucket":             "false",
			},
		})
	if err != nil {
		return fmt.Errorf("failed to start restore job: %w", err)
	}

	restoreJobId := aws.ToString(restoreOutput.RestoreJobId)

	log.Printf(
		"Restore job started with id %q\n",
		restoreJobId)

	if !params.Wait {
		log.Println(
			"Monitor progress in the AWS Backup console or via the AWS CLI")

		return nil
	}

	if err := restorejob.WaitForCompletion(
		ctx,
		backupClient,
		restoreJobId,
		params.WaitTimeout); err != nil {
		return err
	}

	log.Println("Restore job completed successfully")

	return nil
}