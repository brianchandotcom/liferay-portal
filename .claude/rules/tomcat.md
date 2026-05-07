# Tomcat Lifecycle

Use these procedures whenever a skill needs to start, stop, bounce, or probe the Liferay portal's Tomcat from the current checkout.

## Resolve the Tomcat Directory

`<tomcat>` is the `tomcat-*` directory under `<bundles>`. When more than one exists, pick the one with the highest version number. Resolve it once and reuse it everywhere below.

## Resolve the HTTP Port

The HTTP port is the `port` attribute on the `<Connector>` element with `protocol="HTTP/1.1"` in `<tomcat>/conf/server.xml`. Extract it once and reuse it as `${PORT}`.

## Probe

Tomcat is running when an HTTP request to `http://localhost:${PORT}` succeeds.

## Start

Run **Probe** first. When it succeeds, Tomcat is already running and there is nothing to do. Otherwise, run `startup.sh` from `<tomcat>/bin` and wait until **Probe** succeeds.

## Stop

Run **Probe** first. When it fails, Tomcat is already stopped and there is nothing to do. Otherwise, run `shutdown.sh` from `<tomcat>/bin` and wait until **Probe** fails.

## Bounce

Stop, then start. Each step is a no-op when Tomcat is already in the target state, so the sequence is safe to invoke regardless of the starting condition. Use this when changes to `<bundles>/portal-ext.properties` (such as `feature.flag.*` entries) need to take effect.
