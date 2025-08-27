package cmd

import "github.com/spf13/cobra"

var restoreCmd = &cobra.Command{Use: "restore", Short: "Restore resources from AWS backups"}

func init() {
	rootCmd.AddCommand(restoreCmd)
}