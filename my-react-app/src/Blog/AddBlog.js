export function addBlog({headline, description}) {

	const request = {
		method: 'POST',
		headers: {
			'Authorization': 'Basic ' + btoa('test@liferay.com:learn'),
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			"headline": headline,
			"articleBody": description
		})
	};
	
	console.log('done with setting the variable request.');

	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/sites/20119/blog-postings',
		request
	).then(res => res.json());
}