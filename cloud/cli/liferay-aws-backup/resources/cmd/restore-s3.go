package cmd

import (
	"github.com/aws/aws-sdk-go-v2/service/backup"
	"github.com/spf13/cobra"

	"github.com/liferay/liferay-portal/cloud/cli/liferay-aws-backup/internal/s3restore"
)

var restoreS3Cmd = &cobra.Command{
	Use:   "s3",
	Short: "Restore an S3 bucket from an AWS Backup recovery point",
	RunE: func(cmd *cobra.Command, args []string) error {
		backupClient := backup.NewFromConfig(awsConfig)

		return s3restore.Run(
			cmd.Context(),
			backupClient,
			s3restore.Params{
				BackupVaultName:  backupVaultName,
				IamRoleARN:       awsBackupServiceAssumedIamRoleARN,
				RecoveryPointARN: recoveryPointARN,
				TargetBucketName: targetBucketName,
				Wait:             wait,
				WaitTimeout:      waitTimeout,
			})
	},
}

func init() {
	addAwsBackupServiceAssumedIamRoleArnFlag(restoreS3Cmd)
	addBackupVaultNameFlag(restoreS3Cmd)
	addRecoveryPointArnFlag(restoreS3Cmd)
	addTargetBucketNameFlag(restoreS3Cmd)
	addWaitFlag(restoreS3Cmd)
	addWaitTimeoutFlag(restoreS3Cmd)

	restoreCmd.AddCommand(restoreS3Cmd)
}