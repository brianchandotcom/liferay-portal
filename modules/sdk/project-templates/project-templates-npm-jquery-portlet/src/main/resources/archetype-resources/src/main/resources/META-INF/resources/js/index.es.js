import $ from 'jquery';

export default function(rootElementId) {
	$(`#${rootElementId}`).html('Hello World!');
}