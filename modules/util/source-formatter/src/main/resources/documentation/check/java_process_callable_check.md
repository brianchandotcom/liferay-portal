## JavaProcessCallableCheck

Always assign a `serialVersionUID` to a class that implements `ProcessCallable`.
Without an explicit `serialVersionUID`, for the same given `ProcessCallable`, by
applying the same instrumenting logic on both pitcher and catcher sides, we may
get two instrumented classes with different auto-generated `serialVersionUID`,
which will cause deserialization failure.