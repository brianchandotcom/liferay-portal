# How to contribute

Liferay is developed by its community, including Liferay users, enthusiasts, employees, customers, partners, and others.  As a community member, we strongly encourage you to contribute Liferay's open source project by implementing new features, enhancing existing features, and fixing bugs. We also welcome your participatation in writing documentation and translations to existing documentation.

To maintain the top quality and innovation associated with Liferay, all code changes are reviewed by a core set of Liferay project maintainers.  We encourage you to introduce yourself to the [core maintainer(s)](http://issues.liferay.com/browse/LPS#selectedTab=com.atlassian.jira.plugin.system.project%3Acomponents-panel) of the areas you are contributing to and engage them as you work.

As you have ideas for new features you want to implement, follow the contribution steps outlined in the sections, below. For more details on specific steps, check out Liferay's extensive Wiki, including articles on [Understanding and Improving Liferay](http://www.liferay.com/community/wiki/-/wiki/tag/understanding+and+improving+liferay). Lastly, visit the links listed in *Additional Resources* section, below.

## Getting Started

* Signup for a [JIRA Account](http://issues.liferay.com).
* Signup for a [GitHub account](https://github.com/signup/free).
* [Submit a ticket](http://issues.liferay.com) for your issue, following the [established JIRA process](http://www.liferay.com/community/wiki/-/wiki/Main/JIRA). If a ticket already exists for the issue, participate via that ticket.
  * Describe the issue clearly. If it is a bug, include steps to reproduce it.
  * Choose an appropriate Category for the issue.
  * Select in the earliest version of the product affected by the issue.
* Fork the repository applicable to your issue. Liferay's core source code resides in the [liferay-portal](https://github.com/liferay/liferay-portal) repository. Liferay's plugin source code resides in the [liferay-plugins](https://github.com/liferay/liferay-plugins) repository.
* Read [Contributing to Liferay using Git and Github](http://www.liferay.com/community/wiki/-/wiki/Main/Contribute+using+Git+and+GitHub).

## Making Changes

* Create a branch from an existing branch (typically the *master* branch) from which you want to base your changes.
* Commit logical units of work.
* Follow the [Liferay Development Style](http://www.liferay.com/community/wiki/-/wiki/Main/Development+Style).  If you are using Liferay IDE, use the built-in code formatter accessible via the *Java* &rarr; *Code Style* &rarr; *Formatter* &rarr; *Active Profile* menu.
* Include a reference to your ticket (e.g. LPS-XXXXX) in your commit messages. For example:

        LPS-83432 Make the example in CONTRIBUTING imperative and concrete


* *Test* your changes thoroughly! Consider the wide variety of operating systems, databases, application servers, and other related technologies Liferay supports.  Make sure the bugs your fix in one environment don't break something in a different environment. See [Unit and Integration Tests](http://www.liferay.com/community/wiki/-/wiki/Main/Unit+and+Integration+tests) for details on exectuing Liferay's automated tests.

## Submitting Changes

* Push your changes to your branch in your fork.
* Submit a pull request to the component lead associated with the area to which your issue applies.
* On the LPS ticket, provide a link to your pull request from github.com and respond to the [Contributor License Agreement](http://www.liferay.com/legal/contributors-agreement) displayed by clicking the *Contribute Solution* button.
* You're done!  Well, not quite ... be sure to watch your pull request and respond to any follow-up comments.

## Additional Resources

* [Getting Started as a Liferay Developer](http://www.liferay.com/community/wiki/-/wiki/Main/Getting+started+as+a+Liferay+Developer+in+a+few+steps)
* [Liferay and JIRA](http://www.liferay.com/community/wiki/-/wiki/Main/JIRA)
* [Contribute to Liferay on Github](http://www.liferay.com/community/wiki/-/wiki/Main/Contribute+using+Git+and+GitHub)
* [Liferay Core Development Guidelines](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+Core+Development+Guidelines)
* [Setting up and using Liferay IDE](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+Contributor+Development+Environment+Setup)
* [Contributor License Agreement](http://www.liferay.com/legal/contributors-agreement)
* [General GitHub documentation](http://help.github.com/)
* [GitHub pull request documentation](http://help.github.com/send-pull-requests/)
* [Liferay's IRC channel on freenode.org](http://webchat.freenode.net/?channels=liferay&uio=d4)
