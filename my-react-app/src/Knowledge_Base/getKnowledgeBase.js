export function getKnowledgeBase() {
	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/sites/20119/knowledge-base-articles',
		{method: 'GET'}
	).then(res => res.json());
}