import ReactDOM from 'react-dom';
import React from 'react';
import AppComponent from './components/App';

export default function(elementId) {
	ReactDOM.render(<AppComponent />, document.getElementById(elementId));
}