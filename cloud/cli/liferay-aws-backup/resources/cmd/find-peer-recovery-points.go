package cmd

import (
	"github.com/aws/aws-sdk-go-v2/service/backup"
	"github.com/spf13/cobra"

	"github.com/liferay/liferay-portal/cloud/cli/liferay-aws-backup/internal/recoverypoint"
)

var findPeerRecoveryPointsCmd = &cobra.Command{
	RunE: func(cmd *cobra.Command, args []string) error {
		backupClient := backup.NewFromConfig(awsConfig)

		return recoverypoint.FindPeers(
			cmd.Context(),
			backupClient,
			recoverypoint.Params{
				BackupVaultName:              backupVaultName,
				RecoveryPointARN:             recoveryPointARN,
				RdsSnapshotIdOutputPath:      rdsSnapshotIdOutputPath,
				S3RecoveryPointArnOutputPath: s3RecoveryPointArnOutputPath,
			})

	},
	Short: "Find the S3 and RDS recovery points for a single backup job run",
	Use:   "find-peer-recovery-points",
}

func init() {
	addBackupVaultNameFlag(findPeerRecoveryPointsCmd)
	addRDSSnapshotIdOutputPathFlag(findPeerRecoveryPointsCmd)
	addRecoveryPointArnFlag(findPeerRecoveryPointsCmd)
	addS3RecoveryPointArnOutputPathFlag(findPeerRecoveryPointsCmd)

	rootCmd.AddCommand(findPeerRecoveryPointsCmd)
}