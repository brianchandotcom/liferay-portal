## JavaStopWatchCheck

Do not set the initial value of `StopWatch` to `null`.

When using `StopWatch` to record time for logging, we should set the inital value of `StopWatch` to `new StopWatch()`.

If the user changes the log level at execution time, the following code can result in a NullPointerException.

```java
StopWatch stopWatch = null;

if (_log.isInfoEnabled()) {
    stopWatch = new StopWatch();

    stopWatch.start();
}

...

if (_log.isInfoEnabled()) {
    _log.info("Finished in " + stopWatch.getTime() + " ms");
}
```