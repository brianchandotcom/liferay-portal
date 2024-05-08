import fs from 'fs/promises';

export default async function getProjectWebContextPath() {
	const bnd = await fs.readFile('./bnd.bnd', 'utf-8');

	const parts = 
		bnd
			.split('\n')
			.find(line => line.startsWith('Web-ContextPath:'))
			.split(':');

	if (!parts.length) {
		throw new Error("Project's bnd.bnd file has no Web-ContextPath entry");
	}

	return parts[1].trim();
}
