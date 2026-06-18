# Full Portal Build

## Trigger

- portal-core changed: `portal-impl/**`, `portal-kernel/**`, `portal-test/**`, `portal-web/**`, `support-tomcat/**`, `util-bridges/**`, `util-java/**`, `util-slf4j/**`, `util-taglib/**`. Mandatory in this case — no Gradle deploy path covers these sources.

	A portal-core change of only `*.properties` files does not fire this validation. `ant all` gives no signal for them, and **Source Format** covers them instead.

- OR the deploy set is large enough that one full build is cheaper than per-module deploys. Compare:

	- **Full Portal Build cost** = 8 min (the `ant all` baseline).

	- **Per-Module Compile cost** = 3 min setup + 1 min × ⌈N/4⌉ (4 approximates Gradle's effective parallelism on a dev box).

	N is the deploy set size as defined in [per-module-compile.md](per-module-compile.md). Show the cost math when picking this branch so the developer can override.

## Match

`^(portal-impl|portal-kernel|portal-test|portal-web|support-tomcat|util-bridges|util-java|util-slf4j|util-taglib)/ &! \.properties$`

## Command

```bash
ant all -Dgradle.stop.daemon.enabled=false
```

`ant all` is `clean` + `compile` + `deploy`; the deploy target's marketplace branch deploys every project with a `.lfrbuild-portal` marker.

## Notes

When this fires, **Per-Module Compile** still runs for any modules in the touched set without a `.lfrbuild-portal` marker (`ant all`'s marketplace branch only deploys modules with the marker), and **Integration Test Compile** is obviated for `.lfrbuild-portal` modules.

## Time Estimate

~8 min.