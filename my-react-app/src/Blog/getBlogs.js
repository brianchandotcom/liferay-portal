export function getBlogs() {
	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/sites/20119/blog-postings',
		{method: 'GET'}
	).then(res => res.json());
}