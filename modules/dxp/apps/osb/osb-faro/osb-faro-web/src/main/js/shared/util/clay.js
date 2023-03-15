import {Stack} from 'immutable';

export function addContext(component, name) {
	const {clay = new Stack()} = component.context;

	return {clay: clay.push(name)};
}

export function isIn(component, name) {
	const {clay} = component.context;

	return clay ? clay.peek() === name : false;
}
