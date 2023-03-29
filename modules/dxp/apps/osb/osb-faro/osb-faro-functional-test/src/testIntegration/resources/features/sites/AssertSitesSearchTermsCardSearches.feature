@spira_Sites @Sites @team_FARO @priority_3 @blocked
Feature: Assert Sites Search Terms card highest search
	As a Business User, I should be able to view Search Terms highest search in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert Search Terms highest search
		Given I go to the "Sites" page
		Then I should see a search term named "recreational facilities and services" in the table