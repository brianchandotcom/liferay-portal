export function addDocument({title_doc, description_doc, file}) {
    // console.log(file);
    var data = new FormData();
    data.set('file', file);
    data.append('title',title_doc);
    data.append('articleBody',description_doc);
    // console.log(data.values().next());
    // data.values().next().value.name = title_doc;
	const request = {
		method: 'POST',
		headers: {
			'Authorization': 'Basic ' + btoa('test@liferay.com:learn'),
			'Accept': 'application/json'
		},
		body: data
	};
	
	console.log('done with setting the variable request.');

	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/sites/20119/documents',
		request
	).then(res => res.json());
}