package recoverypoint

import (
	"context"
	"encoding/json"
	"fmt"
	"log"
	"os"
	"strings"
	"time"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/backup"
	"github.com/aws/aws-sdk-go-v2/service/backup/types"
)

type FindPeersOutput struct {
	RdsRecoveryPointArn string `json:"rdsRecoveryPointArn"`
	RdsSnapshotId       string `json:"rdsSnapshotId"`
	S3RecoveryPointArn  string `json:"s3RecoveryPointArn"`
}

type Params struct {
	BackupVaultName              string
	RecoveryPointARN             string
	RdsSnapshotIdOutputPath      string
	S3RecoveryPointArnOutputPath string
}

func FindPeers(
	ctx context.Context,
	backupClient *backup.Client,
	params Params,
) error {
	log.Printf("Describing recovery point %q\n", params.RecoveryPointARN)

	describeRecoveryPointOutput, err := backupClient.DescribeRecoveryPoint(
		ctx,
		&backup.DescribeRecoveryPointInput{
			BackupVaultName:  aws.String(params.BackupVaultName),
			RecoveryPointArn: aws.String(params.RecoveryPointARN),
		})
	if err != nil {
		return fmt.Errorf(
			"failed to describe recovery point %q: %w",
			params.RecoveryPointARN,
			err)
	}

	log.Printf(
		"Found creation date %q for recovery point. Searching for peers in vault %q\n",
		describeRecoveryPointOutput.CreationDate.Format(time.RFC3339),
		params.BackupVaultName)

	recoveryPoints, err := getRecoveryPointsMatchingCreationDate(
		ctx,
		backupClient,
		params,
		describeRecoveryPointOutput)
	if err != nil {
		return err
	}

	rdsRecoveryPoints := make([]types.RecoveryPointByBackupVault, 0, 1)
	s3RecoveryPoints := make([]types.RecoveryPointByBackupVault, 0, 1)

	for _, recoveryPoint := range recoveryPoints {
		switch aws.ToString(recoveryPoint.ResourceType) {
		case "RDS":
			rdsRecoveryPoints = append(rdsRecoveryPoints, recoveryPoint)
		case "S3":
			s3RecoveryPoints = append(s3RecoveryPoints, recoveryPoint)
		}
	}

	if len(rdsRecoveryPoints) != 1 {
		return fmt.Errorf(
			"expected to find 1 RDS recovery point, but found %d",
			len(rdsRecoveryPoints))
	}

	if len(s3RecoveryPoints) != 1 {
		return fmt.Errorf(
			"expected to find 1 S3 recovery point, but found %d",
			len(s3RecoveryPoints))
	}

	rdsRecoveryPointArn := aws.ToString(rdsRecoveryPoints[0].RecoveryPointArn)
	s3RecoveryPointArn := aws.ToString(s3RecoveryPoints[0].RecoveryPointArn)

	_, rdsSnapshotId, found := strings.Cut(
		rdsRecoveryPointArn,
		"snapshot:")
	if !found {
		return fmt.Errorf(
			"could not parse RDS snapshot Id from ARN %q",
			rdsRecoveryPointArn)
	}

	log.Printf("Found RDS snapshot id %q", rdsSnapshotId)
	log.Printf("Found S3 recovery point arn %q", s3RecoveryPointArn)

	if params.RdsSnapshotIdOutputPath != "" {
		if err := os.WriteFile(
			params.RdsSnapshotIdOutputPath,
			[]byte(rdsSnapshotId),
			0644); err != nil {
			return err
		}
	}

	if params.S3RecoveryPointArnOutputPath != "" {
		if err := os.WriteFile(
			params.S3RecoveryPointArnOutputPath,
			[]byte(s3RecoveryPointArn),
			0644); err != nil {
			return err
		}
	}

	outputData := FindPeersOutput{
		RdsRecoveryPointArn: rdsRecoveryPointArn,
		RdsSnapshotId:       rdsSnapshotId,
		S3RecoveryPointArn:  s3RecoveryPointArn,
	}

	jsonData, err := json.Marshal(outputData)
	if err != nil {
		return fmt.Errorf("failed to marshal output to JSON: %w", err)
	}

	fmt.Print(string(jsonData))

	return nil
}

func getRecoveryPointsMatchingCreationDate(
	ctx context.Context,
	backupClient *backup.Client,
	params Params,
	describeRecoveryPointOutput *backup.DescribeRecoveryPointOutput,
) ([]types.RecoveryPointByBackupVault, error) {
	recoveryPoints := make([]types.RecoveryPointByBackupVault, 0, 2)
	sourceCreationDate := *describeRecoveryPointOutput.CreationDate
	timeWindow := time.Second

	paginator := backup.NewListRecoveryPointsByBackupVaultPaginator(
		backupClient,
		&backup.ListRecoveryPointsByBackupVaultInput{
			BackupVaultName: aws.String(params.BackupVaultName),
			ByCreatedAfter:  aws.Time(sourceCreationDate.Add(-timeWindow)),
			ByCreatedBefore: aws.Time(sourceCreationDate.Add(timeWindow)),
		})

	log.Println(
		"Scanning vault for recovery points with matching timestamps")

	for paginator.HasMorePages() {
		page, err := paginator.NextPage(ctx)
		if err != nil {
			return nil, fmt.Errorf(
				"failed to list recovery points for vault %q: %w",
				params.BackupVaultName,
				err)
		}

		for _, recoveryPoint := range page.RecoveryPoints {
			if recoveryPoint.CreationDate.Equal(sourceCreationDate) {
				recoveryPoints = append(recoveryPoints, recoveryPoint)
			}
		}
	}

	return recoveryPoints, nil
}