export function addKnowledgeBase({title, articleBody}) {

	const request = {
		method: 'POST',
		headers: {
			'Authorization': 'Basic ' + btoa('test@liferay.com:learn'),
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			"title": title,
			"articleBody": articleBody
		})
	};
	
	console.log('done with setting the variable request.');

	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/sites/20119/knowledge-base-articles',
		request
	).then(res => res.json());
}