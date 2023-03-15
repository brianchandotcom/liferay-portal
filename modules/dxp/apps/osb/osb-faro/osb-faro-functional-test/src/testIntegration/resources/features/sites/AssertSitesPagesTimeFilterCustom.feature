@spira_Sites @Sites @team_FARO @priority_5
Feature: Assert Sites Pages time filter able to use Custom Range
    As a Business User, I should be able to change time filter in Sites pages

    Background: [Setup] Navigate to Pages tab
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click the "Pages" tab

    Scenario: Custom Range time filter
        Given I click the "Last 30 days" dropdown in the card
        And I click the "Custom Range" button
        And I set start date "July 1 2020" and end date "July 13 2020"
        Then I should see time filter "Jul 1, 2020 - Jul 13, 2020" in card