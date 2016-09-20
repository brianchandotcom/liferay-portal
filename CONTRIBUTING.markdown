# How to Contribute

Liferay is developed by its community consisting of Liferay users, enthusiasts,
employees, customers, partners, and others. We strongly encourage you to
contribute to Liferay's open source projects by implementing new features,
enhancing existing features, and fixing bugs. We also welcome your participation
in our forums, writing documentation, and translating existing documentation.

Liferay is known for its innovative top quality features. To maintain this
reputation, all code changes are reviewed by a core set of Liferay project
maintainers. We encourage you to introduce yourself to the [core
maintainer(s)](https://issues.liferay.com/browse/LPS#selectedTab=com.atlassian.jira.plugin.system.project%3Acomponents-panel)
and engage them as you contribute to the areas they maintain.

As you have ideas for features you want to implement, follow the contribution
steps outlined in the sections, below. For more details on specific steps, and
to get a deeper understanding of Liferay in general, make sure to read Liferay's
official documentation on the [Liferay Developer
Network](https://dev.liferay.com/), for
[using](https://dev.liferay.com/discover/portal) and
[deploying](https://dev.liferay.com/discover/deployment)  Liferay Portal, and
[developing](https://dev.liferay.com/develop) on Liferay. This documentation
contains extensive explanations, examples, and reference material for you to
consult time and time again.

Also, check out Liferay's extensive Wiki, including its articles on
[Understanding and Improving
Liferay](http://www.liferay.com/community/wiki/-/wiki/tag/understanding+and+improving+liferay).
Lastly, visit the links listed in the *Additional Resources* section, below.

## Getting Started

* Sign up for a [JIRA Account](https://issues.liferay.com) to track progress on
the feature, improvement, or bug fix you want to implement. We'll refer to these
as *issues*.
* Sign up for a [GitHub account](https://github.com/signup/free) to access
Liferay's repositories.
* [Submit a ticket](https://issues.liferay.com) for your issue, following the
[established JIRA
process](http://www.liferay.com/community/wiki/-/wiki/Main/JIRA). If a ticket
already exists for the issue, participate via the existing ticket.
  * Describe the issue clearly. If it is a bug, include steps to reproduce it.
  * Select an appropriate category for the issue.
  * Select the earliest version of the product affected by the issue.
* Fork the repository applicable to your issue. Liferay's core source code
resides in the [liferay-portal](https://github.com/liferay/liferay-portal)
repository. Liferay's plugin source code resides in the
[liferay-plugins](https://github.com/liferay/liferay-plugins) repository.
* Read [Contributing to Liferay using Git and
Github](http://www.liferay.com/community/wiki/-/wiki/Main/Contribute+using+Git+and+GitHub).

## Making Changes

* Ensure you have a working Liferay development environment. For some tips on
setting up an efficient workspace, read [Optimal Liferay Core
Development](http://www.liferay.com/es/community/wiki/-/wiki/Main/Optimal+Liferay+Core+Development).
This guide provides detailed instructions on how to set up a highly efficient
development system with the necessary tools and procedures for optimizing common
and repetitive tasks.
* Create a branch from an existing branch (typically the *master* branch) from
which you want to base your changes.
* Commit logical units of work.
* Follow the [Liferay Development
Style](http://www.liferay.com/community/wiki/-/wiki/Main/Development+Style). If
you are using Liferay IDE, use the built-in code formatter accessible via the
*Java* &rarr; *Code Style* &rarr; *Formatter* &rarr; *Active Profile* menu.
* Include a reference to your ticket (e.g. LPS-XXXXX) in your commit messages.
For example:

        LPS-83432 Make the example in CONTRIBUTING imperative and concrete

* *Test* your changes thoroughly! Consider the wide variety of operating
systems, databases, application servers, and other related technologies Liferay
supports. Make sure your changes in one environment don't break something in
another environment. See [Unit and Integration
Tests](http://www.liferay.com/community/wiki/-/wiki/Main/Unit+and+Integration+tests)
for details on executing Liferay's automated tests.

## Submitting Changes

* Push changes in your branch to your fork.
* Send a pull request to the core maintainer of the area to which your changes
apply. You can use [github.com](https://github.com/) to initiate the pull
request, or you can use
[git-pull-request](https://github.com/liferay/git-tools/tree/master/git-pull-request),
a command line tool for issuing pull requests, developed specifically for the
Liferay Community.
* In the LPS ticket, provide a link to your GitHub pull request and respond to
the [Contributor License
Agreement](http://www.liferay.com/legal/contributors-agreement) displayed by
clicking the *Contribute Solution* button.
* You're done! Well, not quite ... be sure to respond to comments and questions
to your pull request until it is closed.

## Additional Resources

* [Getting Started as a Liferay
Developer](http://www.liferay.com/community/wiki/-/wiki/Main/Getting+started+as+a+Liferay+Developer+in+a+few+steps)
* [Optimal Liferay Core
Development](http://www.liferay.com/community/wiki/-/wiki/Main/Optimal+Liferay+Core+Development)
* [Liferay and JIRA](http://www.liferay.com/community/wiki/-/wiki/Main/JIRA)
* [Contribute to Liferay on
Github](http://www.liferay.com/community/wiki/-/wiki/Main/Contribute+using+Git+and+GitHub)
* [Liferay Core Development
Guidelines](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+Core+Development+Guidelines)
* [Setting up and using Liferay
IDE](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+Contributor+Development+Environment+Setup)
* [Contributor License
Agreement](http://www.liferay.com/legal/contributors-agreement)
* [General GitHub documentation](http://help.github.com/)
* [GitHub pull request
documentation](http://help.github.com/send-pull-requests/)
* [Liferay's IRC channel on
freenode.org](http://webchat.freenode.net/?channels=liferay&uio=d4)