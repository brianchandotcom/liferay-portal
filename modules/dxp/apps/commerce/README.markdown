# Emporio / *Developer Preview*

Emporio is an open-source digital commerce platform written in Java.

It was built from the ground up to work with [Liferay Portal](https://github.com/liferay/liferay-portal) which provides web experience management and best-in-breed portal capabilities.

> **Attention:** This project is currently in a *Developer Preview* phase which means it should only be used for test purposes. A stable version that is ready for production will be released in the second half of 2018.

## Why?

While Liferay enthusiasts can use the platform to build marketing sites, customer portals, and intranets, they do not have an integrated Liferay product that helps them build modern digital commerce sites.

Emporio is here not only to bridge that gap, but also to provide vendors the most complete platform to attract, convert and retain customers.

## Quick Start

Requirements: [Liferay Portal 7.1 M1](https://github.com/liferay/liferay-portal)

Clone the Emporio repository (this
[repository](https://github.com/liferay/com-liferay-commerce-private)) to the
same level of your directory tree as your Liferay Home folder. (Liferay Home is
the folder that contains the Tomcat folder for Liferay 7.1).

Then start Liferay Portal 7.1.

In the terminal, navigate to the source code folder (com-liferay-commerce-private).

Windows Users: enter `gradlew.bat deploy -Dbuild.profile=portal`

MacOS/Linux: enter `./gradlew deploy -Dbuild.profile=portal`

This takes a few minutes. When the build is complete, go to
http://localhost:8080 in your browser and select *Commerce* &rarr; *Enable
Features* in the Site Menu to activate Emporio in your Portal instance.

## Bug Reporting

Did you find a bug? Please open a ticket for it at [issues.liferay.com](https://issues.liferay.com).

## Stay Connected

There are many ways for you to learn what's new in Emporio, get answers to
questions, and connect with other Liferay community members.

### Twitter

Follow us on Twitter:

-   [@Liferay](http://twitter.com/Liferay) tweets the Liferay's latest
    announcements

-   [@LiferayDocs](http://twitter.com/Liferaydocs) tweets about new articles

-   [@LiferayEng](http://twitter.com/Liferayeng) tweets from the core engineering team

### Blog

Read details on announcements, engage in discussions, and learn more by following [Liferay's Blog](http://www.liferay.com/community/blogs).

### Forum

Do you have questions? Ask them on our [forums](http://www.liferay.com/community/forums)!

### Chat

Join the conversation on Liferay's Community Chat.

* Get your invite: [community-chat.liferay.com](https://community-chat.liferay.com)
* Enter the chat: [liferay-community.slack.com](https://liferay-community.slack.com)
* Channel: `#emporio`

## License

This project, *Emporio*, is free software ("Licensed
Software"); you can redistribute it and/or modify it under the terms of the [GNU
Lesser General Public License](./LICENSE.txt) as
published by the Free Software Foundation; either version 2.1 of the License, or
(at your option) any later version.

This project is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; including but not limited to, the implied warranty of MERCHANTABILITY,
NONINFRINGEMENT, or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
Public License for more details.

You should have received a copy of the [GNU Lesser General Public
License](./LICENSE.txt) along with this project; if
not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
Floor, Boston, MA 02110-1301 USA
