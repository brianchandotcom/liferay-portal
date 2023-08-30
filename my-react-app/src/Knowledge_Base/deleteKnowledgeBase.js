export function deleteKnowledgeBase(articleId) {
    console.log(articleId.articleId);
	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/knowledge-base-articles/' + articleId.articleId,
		{method: 'DELETE'}
	).then(res => res.json());
}