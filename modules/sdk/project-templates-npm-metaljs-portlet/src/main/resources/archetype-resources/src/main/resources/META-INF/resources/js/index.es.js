'use strict';

import templates from './HelloWorld.soy';
import Component from 'metal-component';
import Soy from 'metal-soy';

class HelloWorld extends Component {}

Soy.register(HelloWorld, templates);

export default function(elementId) {
	new HelloWorld({ element: `#${elementId}` });
}