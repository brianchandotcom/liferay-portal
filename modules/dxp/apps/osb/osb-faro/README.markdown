# Faro

[![Build Status](https://travis-ci.com/liferay/com-liferay-osb-faro-private.svg?token=a43XxxAet6usa4DRSqNr&branch=7.1.x)](https://travis-ci.com/liferay/com-liferay-osb-faro-private)
[![Coverage Status](https://coveralls.io/repos/github/liferay/com-liferay-osb-faro-private/badge.svg?branch=7.1.x&t=Qnhecp)](https://coveralls.io/github/liferay/com-liferay-osb-faro-private?branch=7.1.x)

## Prerequisites

Download Docker for [Mac](https://www.docker.com/docker-mac) or [Windows](https://www.docker.com/docker-windows). If you are running Windows,
make sure "Expose daemon on tcp://localhost:2375 without TLS" on the "General" page in Settings is checked.

### Running Experience Cloud

1. Start Docker.

1. Create the docker image: `./gradlew createDocker` (You only need to do this once).

1. Start the image: `./gradlew startDocker`.

#### Redeploying Faro Apps

1. Navigate to root directory of `com-liferay-osb-faro-private`.

1. Run `./gradlew deploy`.

#### Running Frontend Development Server

1. Run `./gradlew packageRunStart`.

1. Then open [http://localhost:3000](http://localhost:3000).

#### Frontend Architecture

If you are new to the project, here is a non exhaustive list of the technologies we are using. It is important to be familiar
with them if you are going to contribute:

* [Typescript](https://www.typescriptlang.org/) as our programming language.
* [Apollo](https://www.apollographql.com/) for our graphql implementation.
* [Redux](https://redux.js.org/) for state management.
* [React](https://reactjs.org/) for our view layer.
* [Webpack](https://webpack.js.org/) for bundling and fast re-deploying.
* [Babel](https://babeljs.io/) so we can write modern Javascript.
* [Jest](https://facebook.github.io/jest/) as our unit-testing framework.
* [Testing-Library/React](https://testing-library.com/docs/react-testing-library/intro/) as our testing utilities.
* [Sass](https://sass-lang.com/) as our CSS pre-processor.
* [Clay](https://clayui.com/) as our CSS and markup foundation.
* [Lodash](https://lodash.com/) as our JS utility library.
* [Prettier](https://prettier.io/) to format our code.
* [ESLint](https://eslint.org/) as our linter.
* [MJML](https://mjml.io/) as our email framework.

Our code is organized into components and pages. Components are resuable views, and pages are specialized components that are mapped to a URL
using `react-router`. Most of our components are located under `shared`. Our pages will be under another directory that relates to
a sub-application in Faro. Right now there are these: `assets`, `contacts`, `event-analysis`, `experiments`, `settings`, `sites`, `test`, `touchpoints`, and `ui-kit`.

#### Pull Requests

Before you open your pull request, make sure you have these:

* Commit messages that are linked back to a ticket or story on [Jira](https://issues.liferay.com).
* Unit tests (if applicable, and they generally are...).
* All unit tests passing: `./gradlew packageRunTest`.
* Beautifully formatted code: `./gradlew formatSource`.

After submitting your PR, Github will automatically add reviewers. They will either approve your pull request, or leave a review and request some changes. Feel free
to ask questions and clarify, as this is a collaborative process. It's common for most pull requests to have changes requested, so don't sweat it.