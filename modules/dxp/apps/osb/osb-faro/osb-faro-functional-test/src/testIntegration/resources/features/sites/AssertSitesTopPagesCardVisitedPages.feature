@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Top Pages card Visited Pages highest views
	As a Business User, I should be able to view highest viewed visited pages in Sites

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert sites Visited Pages highest views
		Given I go to the "Sites" page
		Then I should see a page named "recontextualize value-added channels" in the table
		And I should see a page named "enhance open-source e-tailers" in the table