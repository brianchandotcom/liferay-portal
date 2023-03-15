@spira_Assets @Assets @team_FARO @priority_5
Feature: Assert Custom Range time filter in assets
    As a Business User, I should be able to change time filter to Custom Range in assets

    Background: [Setup] Navigate to Assets page
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I go to the "Assets" page

    Scenario: Custom Range time filter in assets
        Given I click the "Last 30 days" dropdown in the card
        When I click the "Custom Range" button
        And I set start date "July 1 2020" and end date "July 13 2020"
        Then I should see time filter "Jul 1, 2020 - Jul 13, 2020" in card