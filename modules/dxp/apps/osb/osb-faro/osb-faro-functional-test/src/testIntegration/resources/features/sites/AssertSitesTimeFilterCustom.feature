@spira_Sites @Sites @team_FARO @priority_5
Feature: Assert Sites Activity time filter able to use Custom Range
    As a Business User, I should be able to change time filter to use Custom Range

    Background: [Setup] Navigate to Sites page
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page

    Scenario: Sites Activity time filter using Custom Range
        Given I go to the "Sites" page
        When I click the "Last 30 days" dropdown in the "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card
        And I click the "Custom Range" button
        And I set start date "July 1 2020" and end date "July 13 2020"
        Then I should see time filter "Jul 1, 2020 - Jul 13, 2020" in "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card
        And I should see granularity starts "Jul 1" and ends "Jul 13" in "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card