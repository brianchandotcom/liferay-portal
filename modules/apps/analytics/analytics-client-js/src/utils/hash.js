import objectHash from 'object-hash';

const ignoredProps = ['screenWidth', 'screenHeight', 'devicePixelRatio'];

const hash = value =>
	objectHash(value, {
		algorithm: 'md5',
		excludeKeys: key => ignoredProps.indexOf(key) !== -1,
		unorderedObjects: true,
	});

export {hash};
export default hash;