## Upgrade

In Upgrade classes, we should not be making calls to `*ServiceUtil` classes.
When a column has been added in the new version, it will do so via an
`ALTER TABLE` sql statement. At this point, in the upgrade code, that column has
not been added, but the hibernate XML file assumes it is there in
`portal-hbm.xml`. Any `SELECT` or `UPDATE` call via `*ServiceUtil` methods will
fail.

We need to do it manually via SQL statements.