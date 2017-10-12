import React from 'react';
import ReactDOM from 'react-dom';

const HelloWorld = () => <div>Hello World!</div>;

export default function(elementId) {
	ReactDOM.render(<HelloWorld />, document.getElementById(elementId));
}