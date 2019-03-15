/**
 * @module post
 * @description Perform a post function with a json body.
 * @example
 * import post from '@/shared/rest/post';
 * post(client)('/process', { name: "Example" }).then(res => console.log(res));
 */
const postCurryed = client => (url, jsonBody) => {
	const config = {
		body: JSON.stringify(jsonBody),
		headers: {
			'Content-Type': 'application/json'
		},
		method: 'post'
	};

	return client(url, config);
};

export default postCurryed;