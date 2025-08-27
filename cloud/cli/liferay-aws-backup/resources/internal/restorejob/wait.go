package restorejob

import (
	"context"
	"errors"
	"fmt"
	"log"
	"time"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/service/backup"
	"github.com/aws/aws-sdk-go-v2/service/backup/types"
)

func WaitForCompletion(
	ctx context.Context,
	backupClient *backup.Client,
	jobId string,
	timeout time.Duration,
) error {
	log.Printf(
		"Waiting for restore job to complete with timeout %q\n",
		timeout)

	ctx, cancel := context.WithTimeout(ctx, timeout)
	defer cancel()

	ticker := time.NewTicker(30 * time.Second)
	defer ticker.Stop()

	for {
		select {
		case <-ctx.Done():
			return ctx.Err()
		case <-ticker.C:
			err, done := checkJobStatus(ctx, backupClient, jobId)
			if done {
				return err
			}
		}
	}
}

func checkJobStatus(
	ctx context.Context,
	backupClient *backup.Client,
	jobId string,
) (error, bool) {
	describeRestoreJobOutput, err := backupClient.DescribeRestoreJob(
		ctx, &backup.DescribeRestoreJobInput{
			RestoreJobId: aws.String(jobId),
		})
	if err != nil {
		return fmt.Errorf(
			"failed to describe restore job %s: %w",
			jobId,
			err), true
	}

	log.Printf(
		"Current restore job status: %s\n",
		describeRestoreJobOutput.Status)

	switch describeRestoreJobOutput.Status {
	case types.RestoreJobStatusCompleted:
		return nil, true
	case types.RestoreJobStatusFailed, types.RestoreJobStatusAborted:
		errMessage := getRestoreJobErrorMessage(
			jobId,
			describeRestoreJobOutput)

		return errors.New(errMessage), true
	case types.RestoreJobStatusPending, types.RestoreJobStatusRunning:
	default:
		return fmt.Errorf(
				"encountered unexpected restore job status: %s",
				describeRestoreJobOutput.Status),
			true
	}

	return nil, false
}

func getRestoreJobErrorMessage(
	jobId string,
	describeRestoreJobOutput *backup.DescribeRestoreJobOutput,
) string {
	message := fmt.Sprintf(
		"restore job %q failed with status %q",
		jobId,
		describeRestoreJobOutput.Status)

	if statusMessage := aws.ToString(
		describeRestoreJobOutput.StatusMessage); statusMessage != "" {
		return fmt.Sprintf("%s: %s", message, statusMessage)
	}

	return message
}