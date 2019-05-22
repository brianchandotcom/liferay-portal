import objectHash from 'object-hash';

const hash = value =>
	objectHash(value, {
		algorithm: 'md5',
		unorderedObjects: true
	});

export {hash};
export default hash;
