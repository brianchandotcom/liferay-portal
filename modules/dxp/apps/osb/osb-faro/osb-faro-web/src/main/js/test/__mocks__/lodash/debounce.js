export default jest.fn(fn => {
	fn.cancel = jest.fn();

	return fn;
});
