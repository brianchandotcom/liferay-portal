import { Component } from '@angular/core';

@Component({
	selector: '#${artifactId}-root',
	template: `
		<div>{{caption}}</div>
	`
})
export class AppComponent {
	caption = 'Hello world!';
}