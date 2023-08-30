export function deleteDocument(documentId) {
    console.log(documentId.documentId);
	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/documents/' + documentId.documentId,
		{method: 'DELETE'}
	).then(res => res.json());
}