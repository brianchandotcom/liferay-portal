# How to Contribute

Liferay Portal is developed by its community consisting of users, enthusiasts,
employees, customers, partners, and others. We strongly encourage you to
contribute to Liferay's open source projects by implementing new features,
enhancing existing features, and fixing bugs. We also welcome your participation
in our forums, chat, writing documentation, and translating existing
documentation.

Liferay Portal is known for its innovative top quality features. To maintain
this reputation, all code changes are reviewed by a core set of project
maintainers. We encourage you to join our
[Slack Chat](https://communitychat.liferay.com/) and introduce yourself to the
core maintainer(s) and engage them as you contribute to the areas they maintain.

To get a deeper understanding of Liferay Portal in general, make sure to read
Liferay's official documentation on the
[Liferay Developer Network](https://dev.liferay.com/develop/tutorials). This
documentation contains extensive explanations, examples, and reference material
for you to consult time and time again.

For more information, visit the links listed in the
[Additional Resources](#additional-resources) section below.

# Building Liferay Portal

System Requirements:

* Apache Ant
* Gradle
* JDK 8
* Quad Core i7 Processor
* 8 GB Memory

The first step to contributing to Liferay Portal is to clone the liferay-portal
repo from Github and build the platform using Apache Ant.

## Build Liferay Portal:

To build Liferay Portal from source, do the following:

* Fork the liferay-portal repo on Github -
  [https://github.com/liferay/liferay-portal](https://github.com/liferay/liferay-portal).

* Clone the forked repo's master branch (or preferred branch) with no commit
  history:

      git clone https://github.com/<github-username>/liferay-portal --branch master --single-branch --depth 1

* Add the main liferay-portal repo as an upstream for fetching changes:

      git remote add upstream https://github.com/liferay/liferay-portal

* Build master branch from source for testing customizations:

      cd liferay-portal
      ant all

* Test the newly built bundle:

      cd ../bundles
      ./tomcat-9.0.6/bin/startup.[bat|sh]

## Building a Module in liferay-portal

A module can be built from within liferay-portal without recompiling the whole
platform. You must, however, compile the entire platform once using the method
explained above. This is due to the fact that the initial build initializes the
build environment.

To build a module (e.g., a module located in
`liferay-portal/modules/apps/comment/comment-web`), run

    cd liferay-portal/modules
    ../gradlew :apps:comment:comment-web:deploy

The following messages should appear in the bundle logs:

      20:31:55,899 INFO  [fileinstall-/Users/jamie/liferay/repos/contrib/bundles/osgi/modules][BundleStartStopLogger:38] STOPPED com.liferay.comment.web_1.1.8 [243]

      20:31:56,038 INFO  [Refresh Thread: Equinox Container: 208f839d-2b6b-0017-1f31-cc9c6b1fc91b][BundleStartStopLogger:35] STARTED com.liferay.comment.web_1.1.8 [243]

# Making Changes

When making changes, it's best to start off by creating a ticket in
[JIRA](https://issues.liferay.com) and referencing the ticket number from within
any commits and pull requests.

## JIRA

Create a ticket in JIRA by doing the following:

* Sign up for a [JIRA Account](https://issues.liferay.com) to track progress on
  the feature, improvement, or bug fix you want to implement. We'll refer to
  these as *issues* from now on.

* [Submit a ticket](https://issues.liferay.com) for your issue. Make sure to
  define/complete the below actions for your ticket.

    * Describe the issue clearly. If it is a bug, include steps to reproduce it.
    * Select an appropriate category for the issue.
    * Select the earliest version of the product affected by the issue.
    * Respond to the Contributor License Agreement displayed by clicking the
      *Contribute Solution* button.

    If a ticket already exists for the issue, participate via the existing
    ticket.

## Github

Submit your custom changes to Github using the following process:

* Create a topic branch to hold your changes based on upstream/master:

      git fetch upstream
      git checkout -b my-custom-change upstream/master

* Commit logical units of work including a reference to your ticket in JIRA. For
  example:

      LPS-83432 Make the example in CONTRIBUTING imperative and concrete

* Test your changes thoroughly! Consider the wide variety of operating systems,
  databases, application servers, and other related technologies Liferay Portal
  supports. Make sure your changes in one environment don't break something in
  another environment.

* Before pushing your branch to your fork on Github, it's a good idea to rebase
  on the updated version of upstream/master:

      git fetch upstream
      git rebase upstream/master

* Push changes in your branch to your fork:

      git push origin my-custom-change

* Submit the pull request to the liferay/liferay-portal repo.

* In the LPS ticket, provide a link to your GitHub pull request

You're done! Well, not quite---be sure to respond to comments and questions to
your pull request until it is closed.

# Tooling

Creating customizations and debugging code can be made easier using tooling.

Install the following tools to aid in development:

* [Blade CLI](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-1/installing-blade-cli)

* [Liferay Dev Studio](https://dev.liferay.com/develop/tutorials/-/knowledge_base/7-1/installing-liferay-ide)

* [IntelliJ Modules Setup Script](https://github.com/holatuwol/liferay-intellij)

# Additional Resources

* [Liferay Community Site](http://community.liferay.com)
* [Liferay Community Slack Chat](https://communitychat.liferay.com/)
* [Contributor License Agreement](https://www.liferay.com/legal/contributors-agreement)
* [General GitHub documentation](http://help.github.com/)
* [GitHub pull request documentation](http://help.github.com/send-pull-requests/)