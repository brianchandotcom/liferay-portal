import '../css/main.scss';
import 'clay-css/lib/css/atlas.css';
import Container from './Container';
import React from 'react';
import ReactDOM from 'react-dom';

window.Liferay = {
	Language: {
		available: {
			'en_US': 'aosidopaisd',
			'es_ES': 'aosidopaisd'
		}
	}
};

export default function(id, props, context) {
	ReactDOM.render(<Container />, document.getElementById(id));
}
