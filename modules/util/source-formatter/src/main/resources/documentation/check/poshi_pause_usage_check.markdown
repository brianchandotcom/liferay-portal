## PoshiPauseUsageCheck

Add a comment with a reference to a JIRA ticket that documents the explanation for the pause.

### Example

```
definition {

	macro assertAccountActivitiesChart {
		...

		// Pausing X seconds due to LRQA-0000

		Pause(locator1 = "3000");

		...
	}

}
```