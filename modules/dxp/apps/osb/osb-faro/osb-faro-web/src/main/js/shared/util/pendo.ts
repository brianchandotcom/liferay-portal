import {Project, User} from './records';

export class Pendo {
	project: Project;
	currentUser: User;

	constructor({currentUser, project}: {currentUser: User; project: Project}) {
		this.currentUser = currentUser;
		this.project = project;
	}

	get data(): {
		account: {
			id?: string;
			name: string;
			planLevel: string;
		};
		visitor: {
			email: string;
			fullName: string;
			id: string;
			role: string;
		};
	} {
		return {
			account: {
				...(this.project.corpProjectUuid && {
					id: this.project.corpProjectUuid
				}),
				name: this.project.corpProjectName,
				planLevel: this.project.faroSubscription.get('name')
			},
			visitor: {
				email: this.currentUser.emailAddress,
				fullName: this.currentUser.name,
				id: this.currentUser.id,
				role: this.currentUser.roleName
			}
		};
	}

	initialize() {
		if (pendo?.isReady?.()) {
			return pendo.identify(this.data);
		}

		return pendo.initialize(this.data);
	}
}
