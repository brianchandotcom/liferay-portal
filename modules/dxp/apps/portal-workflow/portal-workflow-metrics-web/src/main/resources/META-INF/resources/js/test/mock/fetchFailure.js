const fetchFailure = data => ({
	delete: () => Promise.reject(data),
	get: () => Promise.reject(data),
	post: () => Promise.reject(data),
	put: () => Promise.reject(data)
});

export default fetchFailure;