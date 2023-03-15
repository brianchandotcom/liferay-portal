@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Individuals Overview time filter changes to 180 days
    As a Business User, I should be able to change individual time filter to 180 days

    Background: [Setup] Navigate to Individuals page
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I go to the "Individuals" page

    Scenario: Change time filter in individuals
        Given I click the "Last 30 days" dropdown in the "Active Individuals" card
        When I click the "More Preset Periods" button
        And I click the "Last 180 days" button
        Then I should see "180" columns in the bar graph table