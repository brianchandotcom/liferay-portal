# Jira REST API

All Jira interactions go through the Jira Cloud REST API at `liferay.atlassian.net` using `curl`. Do not use Atlassian MCP tools, Jira CLI wrappers, or any other Jira integration. Every Jira read or write must be a `curl` call against the REST API.

## Authentication

Authenticate every request with the `${JIRA_API_USER}` and `${JIRA_API_TOKEN}` environment variables. With `curl`, pass them through the `--user` flag:

```bash
curl \
	--silent \
	--url "https://liferay.atlassian.net/rest/api/3/<endpoint>" \
	--user "${JIRA_API_USER}:${JIRA_API_TOKEN}"
```