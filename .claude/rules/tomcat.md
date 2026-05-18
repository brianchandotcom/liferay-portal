# Tomcat Lifecycle

Use these procedures whenever a skill needs to start, stop, bounce, or probe the Liferay portal's Tomcat from the current checkout.

## Resolve the Tomcat Directory

`<tomcat>` is the `tomcat-*` directory under `<bundles>`. When more than one exists, pick the one with the highest version number. Resolve it once and reuse it everywhere below.

## Resolve the HTTP Port

The HTTP port is the `port` attribute on the `<Connector>` element with `protocol="HTTP/1.1"` in `<tomcat>/conf/server.xml`. Extract it once and reuse it as `${PORT}`.

## Probe

Tomcat is **ready** when an HTTP request to `http://localhost:${PORT}` succeeds. A failed request does not mean Tomcat is stopped — it may still be starting. Distinguish the two by checking for the Tomcat JVM process:

- Process present and HTTP failing → **starting**. Watch the bundle logs for progress. Booting against a fresh database adds several minutes before the HTTP port accepts requests.
- Process absent → **stopped**.

## Start

When Tomcat is already starting, skip the launch and go straight to polling. Otherwise, run `<tomcat>/bin/catalina.sh jpda start`. Either way, poll until Tomcat is **ready** before continuing.

## Stop

When no Tomcat process is running, there is nothing to do. Otherwise, run `shutdown.sh` from `<tomcat>/bin` and wait until the process exits.

## Bounce

Stop, then start. Each step is a no-op when Tomcat is already in the target state, so the sequence is safe to invoke regardless of the starting condition. Use this when changes to `<bundles>/portal-ext.properties` (such as `feature.flag.*` entries) need to take effect.