@spira_Sites @Sites @team_FARO @priority_2
Feature: Assert Custom Range over 365 days returns warning message
    As a Business User, I should not be able to set Custom Range over 365 days

    Background: [Setup] Navigate to Sites page
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page

    Scenario: Custom Range over 365 days
        Given I go to the "Sites" page
        When I click the "Last 30 days" dropdown in the "LIFERAY-DATASOURCE-FARO-EXAMPLE Activities" card
        And I click the "Custom Range" button
        And I set start date "July 1 2019" and end date "July 13 2020"
        Then I should see an error saying the range exceeds the maximum range