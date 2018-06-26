# Emporio

Emporio is an open source digital commerce platform written in Java. It was
built from the ground up to work with Liferay Portal so that Liferay developers
can leverage fully integrated web content management systems and best-in-breed
portal capabilities in their commerce projects.

Liferay designed Emporio for commerce projects that are very large or complex;
it can support millions of products in challenging B2B industries.

## Key Features

* Catalog Management and Product Browsing
* Checkout, Discounts and Pricing
* Payments and Tax Management
* Shipping Management
* Order Management
* Customer Account Management
* Multicurrency and Multilanguage

## Quick Start

Requirements: [Liferay Portal 7.1 B3](https://github.com/liferay/liferay-portal)

1. Clone the Emporio repository (this [repository](https://github.com/liferay/com-liferay-commerce-private))
to the same level of your directory tree as your Liferay Home folder (Liferay
Home is the folder that contains the Tomcat folder for Liferay 7.1).

2. Start Liferay Portal 7.1.

In the terminal, navigate to the source code folder
(`com-liferay-commerce-private`).

Windows Users: enter `gradlew.bat deploy -Dbuild.profile=portal`

MacOS/Linux: enter `./gradlew deploy -Dbuild.profile=portal`

This takes a few minutes. When the build is complete, go to--or refresh--
http://localhost:8080 in your browser. Emporio's features are now available in
your Portal instance.

## Bug Reporting

Did you find a bug? Please open a ticket for it at [issues.liferay.com](https://issues.liferay.com).

## Stay Connected

There are many ways for you to learn what's new in Emporio, get answers to
questions and connect with other Liferay community members.

### Twitter

Follow us on Twitter:

- [@Liferay](http://twitter.com/Liferay) Latest announcements
- [@LiferayDocs](http://twitter.com/Liferaydocs) New articles
- [@LiferayEng](http://twitter.com/Liferayeng) Tweets from the core engineering
team

### Blog

Read details on announcements, engage in discussions and learn more by following
[Liferay's Blog](http://www.liferay.com/community/blogs).

### Forum

Do you have questions? Ask them on our
[forums](http://www.liferay.com/community/forums).

### Chat

Join the conversation on Liferay's Community Chat.

* Get your invite: [community-chat.liferay.com](https://community-chat.liferay.com)
* Enter the chat: [liferay-community.slack.com](https://liferay-community.slack.com)
* Channel: `#emporio`

## License

This project, *Emporio*, is free software ("Licensed Software"); you can
redistribute it and/or modify it under the terms of the [GNU Lesser General Public License](./LICENSE.txt)
as published by the Free Software Foundation; either version 2.1 of the License,
or (at your option) any later version.

This project is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; including but not limited to, the implied warranty of MERCHANTABILITY,
NONINFRINGEMENT, or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
Public License for more details.

You should have received a copy of the [GNU Lesser General Public License](./LICENSE.txt)
along with this project; if not, write to the Free Software Foundation, Inc., 51
Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA