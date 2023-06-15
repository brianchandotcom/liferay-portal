	public async importIssues(issues: string[]): Promise<any> {
		const _issues = issues
			.map((name) => name.trim().toUpperCase())
			.filter(Boolean);

		const jiraIssues = await fetcher('/jira/getissues', {
			body: JSON.stringify({
				issues: _issues,
			}),
			headers: this.headers,
			method: 'POST',
		});

		return jiraIssues;
	}
