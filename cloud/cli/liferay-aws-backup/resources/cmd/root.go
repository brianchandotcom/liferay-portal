package cmd

import (
	"context"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/spf13/cobra"
)

var (
	awsConfig aws.Config
	rootCmd   = &cobra.Command{
		Short: "Manage Liferay backup operations on AWS",
		Use:   "liferay-aws-backup",
	}
)

func Execute() {
	cobra.CheckErr(rootCmd.Execute())
}

func init() {
	cobra.OnInitialize(func() {
		cfg, err := config.LoadDefaultConfig(context.Background())

		cobra.CheckErr(err)

		awsConfig = cfg
	})
}