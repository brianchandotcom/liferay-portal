const fetchFailure = data => ({
	get: () => Promise.reject(data),
	post: () => Promise.reject(data),
	put: () => Promise.reject(data)
});

export default fetchFailure;