/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checks;

/**
 * @author Peter Shin
 */
public class CQLKeywordCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		for (String s : _CASSANDRA_COMMANDS) {
			content = content.replaceAll("(?i)\\b" + s + "\\b", s);
		}

		for (String s : _CASSANDRA_KEYWORDS) {
			content = content.replaceAll("(?i)\\b" + s + "\\b", s);
		}

		for (String s : _CREATE_KEYSPACE_KEYWORDS) {
			content = content.replaceAll("(?i)\\b" + s + "\\b", s);
		}

		return content;
	}

	private static final String[] _CASSANDRA_COMMANDS = {
		"ALTER KEYSPACE", "ALTER TABLE", "ALTER TYPE", "ALTER USER", "BATCH",
		"CREATE INDEX", "CREATE KEYSPACE", "CREATE TABLE", "CREATE TRIGGER",
		"CREATE TYPE", "CREATE USER", "DELETE", "DROP INDEX", "DROP KEYSPACE",
		"DROP TABLE", "DROP TRIGGER", "DROP TYPE", "DROP USER", "GRANT",
		"INSERT", "LIST PERMISSIONS", "LIST USERS", "REVOKE", "SELECT",
		"TRUNCATE", "UPDATE", "USE"
	};

	private static final String[] _CASSANDRA_KEYWORDS = {
		"ADD", "AGGREGATE", "ALL", "ALLOW", "ALTER", "AND", "ANY", "APPLY",
		"AS", "ASC", "ASCII", "AUTHORIZE", "BATCH", "BEGIN", "BIGINT", "BLOB",
		"BOOLEAN", "BY", "CLUSTERING", "COLUMNFAMILY", "COMPACT", "CONSISTENCY",
		"COUNT", "COUNTER", "CREATE", "CUSTOM", "DECIMAL", "DELETE", "DESC",
		"DISTINCT", "DOUBLE", "DROP", "EACH_QUORUM", "ENTRIES", "EXISTS",
		"FILTERING", "FLOAT", "FROM", "FROZEN", "FULL", "GRANT", "IF", "IN",
		"INDEX", "INET", "INFINITY", "INSERT", "INT", "INTO", "KEY", "KEYSPACE",
		"KEYSPACES", "LEVEL", "LIMIT", "LIST", "LOCAL_ONE", "LOCAL_QUORUM",
		"MAP", "MATERIALIZED", "MODIFY", "NAN", "NORECURSIVE", "NOSUPERUSER",
		"NOT", "OF", "ON", "ONE", "ORDER", "PARTITION", "PASSWORD", "PER",
		"PERMISSION", "PERMISSIONS", "PRIMARY", "QUORUM", "RENAME", "REVOKE",
		"SCHEMA", "SELECT", "SET", "STATIC", "STORAGE", "SUPERUSER", "TABLE",
		"TEXT", "THREE", "TIME", "TIMESTAMP", "TIMEUUID", "TO", "TOKEN",
		"TRUNCATE", "TTL", "TUPLE", "TWO", "TYPE", "UNLOGGED", "UPDATE", "USE",
		"USER", "USERS", "USING", "UUID", "VALUES", "VARCHAR", "VARINT", "VIEW",
		"WHERE", "WITH", "WRITETIME"
	};

	private static final String[] _CREATE_KEYSPACE_KEYWORDS = {
		"AND DURABLE_WRITES", "CREATE SCHEMA", "IF NOT EXISTS",
		"WITH REPLICATION"
	};

}