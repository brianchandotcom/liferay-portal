## Gradle Task Creation

A task should be declared in a separate line before the closure.

### Example

Incorrect:

```
task hello {
    doLast {
        println 'Hello world!'
    }
}
```

Correct:

```
task hello

hello {
    doLast {
        println 'Hello world!'
    }
}
```