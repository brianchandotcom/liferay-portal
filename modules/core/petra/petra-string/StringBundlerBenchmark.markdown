# StringBundlerBenchmark results conclusion

| Most optimal String concatenation method under different case/platform combinations | JDK8 | JDK21 Runtime with JDK8 target version | JDK21 Runtime with JDK21 target version |
| ------------- | ------------- | ------------- | ------------- |
| **2 Strings** | String.concat() | String.concat() | String.concat() |
| **3+ Strings with plain static concatenation sequence** | StringBundler | StringBundler | "+" |
| **3+ Strings with conditional concatenation sequence** | StringBundler | StringBundler | StringBundler |

The outlier is the **"+"** for "**JDK21 Runtime with JDK21 target version**"/"**3+ Strings with plain static concatenation sequence**" case. It is way faster than other string concatenations solutions.

This is due to the
[Indify String Concatenation](https://openjdk.org/jeps/280) optimization(which requires 9+ javac to generate the proper *invokedynamic* bytecode).

To be exact, as long as the target version is 9+, the **"+"** solution will always win for "**3+ Strings with plain static concatenation sequence**" case.