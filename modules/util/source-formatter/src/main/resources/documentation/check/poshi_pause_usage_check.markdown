## PoshiPauseUsageCheck

Add a comment with explanation on why the pause is there.

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