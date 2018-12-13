// import '../css/main.scss';
import ReactDOM from 'react-dom';
// eslint-disable-next-line no-unused-vars
import React from 'react';
import AppComponent from './components/App';

export default function(elementId) {
	ReactDOM.render(<AppComponent />, document.getElementById(elementId));
}