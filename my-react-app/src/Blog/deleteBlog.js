export function deleteBlog(blogId) {
    console.log(blogId.blogId);
	return Liferay.Util.fetch(
		'/o/headless-delivery/v1.0/blog-postings/' + blogId.blogId,
		{method: 'DELETE'}
	).then(res => res.json());
}