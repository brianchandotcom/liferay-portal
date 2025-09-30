/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const LIFERAY_HOST = process.env.LIFERAY_HOST || '';
const LIFERAY_SPACE_ID = process.env.LIFERAY_SPACE_ID || '';

const createCMSBlogEntryOnLiferay = async ({
	blog,
}: {
	blog: ReturnType<typeof createCMSBlog>;
}) => {
	const endpoint = `${LIFERAY_HOST}/o/cms/blogs/scopes/${LIFERAY_SPACE_ID}`;
	const init = {
		method: 'POST',
		headers: {
			'accept': 'application/json',
			'content-type': 'application/json',
			'Authorization': 'Basic ' + btoa('test@liferay.com:test'),
		},
		body: JSON.stringify(blog),
	};

	if (process.env.DEBUG) {
		console.log(endpoint, init);

		return Promise.resolve(new Response());
	}

	return fetch(endpoint, init);
};

const getBase64Image = async ({id}: {id: number}) => {
	const response = await fetch(
		`https://placehold.co/600x400/00318F/FFF.png?text=Blog%20Post%20%23${id}`
	);

	const buffer = await response.arrayBuffer();
	const base64 = Buffer.from(buffer).toString('base64');

	return base64;
};

const createCMSBlog = ({blogId, image}: {blogId: number; image: string}) => {
	const friendlyUrlPath = `cms-blog-furl-${blogId}`;

	return {
		id: blogId,
		defaultLanguageId: 'en_US',
		friendlyUrlPath,
		friendlyUrlPath_i18n: {
			sv_SE: friendlyUrlPath,
			pt_BR: friendlyUrlPath,
			fr_FR: friendlyUrlPath,
			ja_JP: friendlyUrlPath,
			ca_ES: friendlyUrlPath,
			de_DE: friendlyUrlPath,
			hu_HU: friendlyUrlPath,
			ar_SA: friendlyUrlPath,
			fi_FI: friendlyUrlPath,
			en_US: friendlyUrlPath,
			zh_CN: friendlyUrlPath,
			es_ES: friendlyUrlPath,
			nl_NL: friendlyUrlPath,
		},
		keywords: ['lorem', 'ipsum'],
		objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
		content:
			'<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque non justo ut tellus eleifend accumsan nec nec neque. In condimentum ante ligula, vel laoreet ipsum fringilla et.<strong> Aenean efficitur fermentum ligula id commodo. </strong>Aenean sit amet tempus odio, vitae viverra risus.<em> Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae;</em> Fusce mauris mi, sagittis nec ligula ut, viverra dapibus leo. Nulla ultricies, diam vel lacinia sodales, ex urna lobortis est, non dapibus tortor tortor id metus. Phasellus ullamcorper nisi quam, eu malesuada risus faucibus ut. Donec viverra lectus molestie commodo varius. Curabitur lacinia sed leo nec interdum. Etiam eu neque nec erat hendrerit dictum. Ut et fermentum quam. Integer porttitor faucibus orci in accumsan. Phasellus mattis finibus libero, sit amet convallis ipsum volutpat at.</p>\n\n<p>Donec fringilla arcu sed mollis semper. Aenean feugiat sagittis quam non porta. Curabitur euismod tincidunt malesuada. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. <strong>Praesent malesuada sodales magna dignissim varius. </strong>In fermentum velit sit amet porta congue. In in volutpat nisl. Donec feugiat, lorem id vestibulum aliquam, sapien libero cursus quam, non mollis magna turpis iaculis leo. Curabitur mollis ante enim, a vestibulum ipsum commodo non. Quisque aliquam at nulla eu suscipit. Vivamus eleifend congue dignissim. Praesent condimentum mollis accumsan. Ut quis mattis erat. Duis et porttitor lectus.</p>\n\n<p>Pellentesque lobortis lorem sodales, euismod quam eu, accumsan tellus. Sed commodo eros ut ante porttitor pretium. Donec vel turpis eget turpis cursus fermentum. Vestibulum sollicitudin, mauris eleifend tristique ullamcorper, sem ante iaculis dolor, at laoreet justo arcu sed urna. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dictum quam ac lectus vehicula vulputate. Praesent at tellus ac mi tristique congue sit amet et neque. Quisque a blandit dolor. Mauris volutpat eros non metus faucibus iaculis. <code>Quisque fringilla fermentum nunc sed vehicula.</code> Mauris vel porttitor lorem, sed porttitor mi. Nunc in magna non mauris varius porttitor a non purus. Curabitur pulvinar, erat eget condimentum efficitur, purus nibh pulvinar tellus, id tempor quam dolor vitae mi.</p>\n\n<p>Duis nec velit sed purus luctus blandit. Ut lacus nisl, vehicula et varius vitae, posuere non massa. Duis sapien lectus, viverra eget libero vel, ultrices bibendum dui. Nam gravida odio ante, non congue dolor interdum vitae. Sed eleifend nunc nisi, sed pretium elit efficitur eu. Nullam justo nisi, efficitur ac finibus non, dignissim eget mi. Aliquam lobortis sagittis mattis. Vestibulum tincidunt, mi ut fermentum gravida, diam nunc fermentum nunc, eu commodo massa nibh quis nulla. Etiam porttitor pretium molestie. Donec lacus urna, interdum vitae eleifend eu, aliquam eu tellus.</p>\n\n<p>Fusce ullamcorper magna eu ante varius laoreet. Sed et nisi sit amet risus imperdiet iaculis. Quisque vulputate consectetur arcu, at euismod lorem egestas ut. Sed laoreet hendrerit eros, nec feugiat leo vestibulum a. Quisque ac accumsan dolor. Suspendisse ornare tincidunt lectus sit amet aliquam. Proin consequat imperdiet lacus, quis elementum eros sodales eu. Praesent a ipsum risus. Donec ipsum ligula, iaculis vel ultricies ut, tempus vitae lorem. Cras vestibulum ligula at tortor dignissim tincidunt. Duis placerat sem eu commodo tristique. Nam at odio felis.</p>',
		subtitle: `Testing Blog Post #${blogId}`,
		title: `Blog Post #${blogId}`,
		coverImage: {
			fileBase64: image,
			mimeType: 'image/png',
			name: `blog-${blogId}.png`,
		},
	};
};

const generateCMSBlobModel = async function* ({blogs}: {blogs: number}) {
	let blogId = 0;
	while (blogId++ < blogs) {
		const image = await getBase64Image({id: blogId});
		yield createCMSBlog({blogId, image});
	}
};

(async () => {
	for await (const blog of generateCMSBlobModel({blogs: 50})) {
		try {
			const response = await createCMSBlogEntryOnLiferay({blog});

			if (!response.ok) {
				console.log(response);
				console.log(await response.text());
				throw new Error('creation error');
			}

			console.log('Success:', blog.title);
		}
		catch (error) {
			console.error(error);
		}
	}
})();
