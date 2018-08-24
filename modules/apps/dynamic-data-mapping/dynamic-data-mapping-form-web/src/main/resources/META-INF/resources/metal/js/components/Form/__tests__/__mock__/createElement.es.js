const setAttribute = (node, attributes) => {
	attributes.map(({key, value}) => node.setAttribute(key, value));

	return node;
};

const createElement = object => {
	const {attributes, tagname} = object;

	return setAttribute(document.createElement(tagname), attributes);
};

export default createElement;